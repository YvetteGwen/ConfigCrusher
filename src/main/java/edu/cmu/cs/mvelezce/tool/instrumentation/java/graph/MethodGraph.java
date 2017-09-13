package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;

import java.util.*;

/**
 * Created by mvelezce on 5/3/17.
 */
public class MethodGraph {

    // TODO create a single exit block for the graph
    private MethodBlock entryBlock = new MethodBlock("entry");
    private MethodBlock exitBlock = new MethodBlock("exit");
    private Map<String, MethodBlock> blocks = new HashMap<>();
    private Map<MethodBlock, Set<MethodBlock>> blocksToDominators = new HashMap<>();

    public MethodGraph() {
        this.blocks.put(this.entryBlock.getID(), this.entryBlock);
        this.blocks.put(this.exitBlock.getID(), this.exitBlock);
    }

    public MethodBlock getImmediatePostDominator(MethodBlock methodBlock) {
        MethodGraph reversedGraph = this.reverseGraph();
        MethodBlock immediatePostDominator = reversedGraph.getImmediateDominator(methodBlock);

        return this.getMethodBlock(immediatePostDominator.getID());
    }

    public void addMethodBlock(MethodBlock methodBlock) {
        this.blocks.put(methodBlock.getID(), methodBlock);
    }

    public void addEdge(MethodBlock from, MethodBlock to) {
        from.addSuccessor(to);
        to.addPredecessor(from);
    }

    public void calculateDominators() {
        for(MethodBlock block : this.blocks.values()) {
            this.blocksToDominators.put(block, new HashSet<>(this.blocks.values()));
        }

        Set<MethodBlock> dominators = new HashSet<>();
        dominators.add(this.entryBlock);
        this.blocksToDominators.put(this.entryBlock, dominators);

        Set<MethodBlock> blocks = new HashSet<>(this.blocks.values());
        blocks.remove(this.entryBlock);

        boolean change = true;

        while (change) {
            change = false;

            for(MethodBlock block : blocks) {
                dominators = new HashSet<>();
                dominators.add(block);

                Set<MethodBlock> predecessorsDominators = new HashSet<>(this.blocks.values());

                for(MethodBlock predecessor : block.getPredecessors()) {
                    predecessorsDominators.retainAll(this.blocksToDominators.get(predecessor));
                }

                dominators.addAll(predecessorsDominators);
                Set<MethodBlock> previousDominators = this.blocksToDominators.get(block);

                if(!previousDominators.equals(dominators)) {
                    change = true;
                    this.blocksToDominators.put(block, dominators);
                }
            }
        }


//        for(Map.Entry<MethodBlock, Set<MethodBlock>> blockToDominators : this.blocksToDominators.entrySet()) {
//            System.out.println(blockToDominators.getKey() + " dominated by " + blockToDominators.getValue());
//        }
    }

    public Map<MethodBlock, Set<MethodBlock>> getDominators() {
        if(this.blocksToDominators.isEmpty()) {
            this.calculateDominators();
        }

        return this.blocksToDominators;
    }

    public MethodBlock getImmediateDominator(MethodBlock start) {
        System.out.println(this.toDotString("reverse"));

        this.getDominators();
        Set<MethodBlock> dominators = new HashSet<>(this.blocksToDominators.get(start));
        dominators.remove(start);

        Set<MethodBlock> ids = new HashSet<>();

        for(MethodBlock dominator : dominators) {
            if(dominators.equals(blocksToDominators.get(dominator))) {
//                return dominator;
                ids.add(dominator);
            }
        }

        if(ids.size() > 1) {
            throw new RuntimeException("Multiple ids");
        }

        if(ids.size() == 1) {
            return ids.iterator().next();
        }

        throw new RuntimeException("Could not find an immediate dominator");
    }

    public MethodGraph reverseGraph() {
        MethodGraph reversedGraph = new MethodGraph();
        Set<MethodBlock> blocks = new HashSet<>(this.blocks.values());

        for(MethodBlock block : blocks) {
            MethodBlock newBlock = new MethodBlock(block.getID());
            reversedGraph.addMethodBlock(newBlock);
        }

        for(MethodBlock block : reversedGraph.blocks.values()) {
            block.reset();
        }

        for(MethodBlock block : blocks) {
            for(MethodBlock successor : block.getSuccessors()) {
                MethodBlock newBlock = reversedGraph.blocks.get(block.getID());
                MethodBlock newSuccessorBlock = reversedGraph.blocks.get(successor.getID());
                reversedGraph.addEdge(newSuccessorBlock, newBlock);
            }
        }

        reversedGraph.entryBlock = reversedGraph.blocks.get(this.exitBlock.getID());
        reversedGraph.exitBlock = reversedGraph.blocks.get(this.entryBlock.getID());

        return reversedGraph;
    }

    /**
     * Kosaraju's algorithm
     *
     * @param start
     * @return
     */
    public Set<Set<MethodBlock>> getStronglyConnectedComponents(MethodBlock start) {
        // DFS on the graph to find the order in which the blocks were last visited
        Stack<MethodBlock> visited = new Stack<>();
        Stack<MethodBlock> dfs = new Stack<>();
        dfs.push(start);

        while (!dfs.isEmpty()) {
            MethodBlock currentBlock = dfs.peek();

            if(currentBlock.getSuccessors().isEmpty()) {
                visited.push(dfs.pop());
            }
            else {
                boolean done = true;

                for(MethodBlock successor : currentBlock.getSuccessors()) {
                    if(!visited.contains(successor) && !dfs.contains(successor)) {
                        dfs.push(successor);

                        done = false;
                        break;
                    }
                }

                if(done) {
                    visited.push(dfs.pop());
                }
            }
        }

        // Reverse the graph
        MethodGraph reversedGraph = this.reverseGraph();

        // DFS in order of last visited block from the first pass
        Set<Set<MethodBlock>> stronglyConnectedComponents = new HashSet<>();

        while (!visited.isEmpty()) {
            dfs.push(visited.pop());
            Set<MethodBlock> stronglyConnectedComponent = new HashSet<>();

            while (!dfs.isEmpty()) {
                MethodBlock currentBlock = dfs.peek();
                currentBlock = reversedGraph.getMethodBlock(currentBlock.getID());
                stronglyConnectedComponent.add(currentBlock);

                if(currentBlock.getSuccessors().isEmpty()) {
                    dfs.pop();
                    visited.remove(currentBlock);
                }
                else {
                    boolean done = true;

                    for(MethodBlock successor : currentBlock.getSuccessors()) {
                        if(visited.contains(successor) && !dfs.contains(successor)) {
                            dfs.push(successor);

                            done = false;
                            break;
                        }
                    }

                    if(done) {
                        dfs.pop();
                        visited.remove(currentBlock);
                    }
                }
            }

            stronglyConnectedComponents.add(stronglyConnectedComponent);
        }

        return stronglyConnectedComponents;
    }

    public String toDotString(String methodName) {
        StringBuilder dotString = new StringBuilder("digraph " + methodName + " {\n");

        for(MethodBlock methodBlock : this.blocks.values()) {
            for(MethodBlock successor : methodBlock.getSuccessors()) {
//                if(methodBlock.getLabel().info == null) {
                dotString.append(methodBlock.getID());
//                }
//                else {
//                    dotString.append(methodBlock.getLabel().info);
//                }
//
                dotString.append(" -> ");
//
//                if(successor.getLabel().info == null) {
                dotString.append(successor.getID());
//                }
//                else {
//                    dotString.append(successor.getLabel().info);
//                }
                dotString.append(";\n");
            }
        }

        dotString.append("}");

        return dotString.toString();
    }

    public MethodBlock getMethodBlock(String ID) {
        return this.blocks.get(ID);
    }

    public MethodBlock getMethodBlock(AbstractInsnNode insnNode) {
        return this.getMethodBlock(MethodBlock.asID(insnNode));
    }

    public MethodBlock getMethodBlock(Label label) {
        return this.getMethodBlock(label.toString());
    }

    public Set<MethodBlock> getBlocks() {
        return new HashSet<>(this.blocks.values());
    }

    public int getBlockCount() {
        return this.blocks.size();
    }

    // TODO how to do this?
    public int getEdgeCount() {
        int edges = 0;

        for(MethodBlock methodBlock : this.blocks.values()) {
            edges += methodBlock.getSuccessors().size();
        }

        return edges;
    }

    public MethodBlock getExitBlock() {
        return this.exitBlock;
    }

    public MethodBlock getEntryBlock() {
        return this.entryBlock;
    }

    @Override
    public String toString() {
        return "MethodGraph{" +
                "entryBlock=" + entryBlock +
                ", exitBlock=" + exitBlock +
                ", blocks=" + blocks.values() +
                '}';
    }
}
