package edu.cmu.cs.mvelezce.tool.instrumentation.java.graph;

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by mvelezce on 5/3/17.
 */
public class MethodGraphBuilder {

    private MethodNode methodNode;

    public MethodGraphBuilder(MethodNode methodNode) {
        this.methodNode = methodNode;
    }

    public MethodGraph build() {
        MethodGraph graph = new MethodGraph();

        InsnList instructions = this.methodNode.instructions;
        ListIterator<AbstractInsnNode> instructionsIterator = instructions.iterator();

        AbstractInsnNode instruction = instructionsIterator.next();
        int instructionType = instruction.getType();

        if(instructionType != AbstractInsnNode.LABEL) {
            throw new RuntimeException();
        }

        LabelNode labelNode = (LabelNode) instruction;
        MethodBlock block = new MethodBlock(labelNode);

        while (instructionsIterator.hasNext()) {
            instruction = instructionsIterator.next();
            instructionType = instruction.getType();

//            if(instructionType == AbstractInsnNode.LABEL) {
//                currentLabelNode = (LabelNode) instruction;
////                LabelNode labelNode = (LabelNode) instruction;
//                labelInstructions = new ArrayList<>();
//                MethodBlock methodBlock = new MethodBlock(currentLabelNode.getLabel(), labelInstructions);
//                graph.addMethodBlock(methodBlock);
//            }
//            else
            if(instructionType == AbstractInsnNode.JUMP_INSN) {
                graph.addMethodBlock(block);

                JumpInsnNode jumpInsn = (JumpInsnNode) instruction;
                block = new MethodBlock(jumpInsn.label);
                graph.addMethodBlock(block);

                AbstractInsnNode nextInstruction = instruction.getNext();

                if(nextInstruction.getType() == AbstractInsnNode.LABEL) {
                    labelNode = (LabelNode) nextInstruction;
                    block = new MethodBlock(labelNode);
                    graph.addMethodBlock(block);
                }
                else {
                    throw new RuntimeException();
                }

//                AbstractInsnNode nextInstruction = instruction.getNext();
//
//                if(nextInstruction.getType() != AbstractInsnNode.LABEL) {
//                    labelInstructions.add(instruction);
//
//                    LabelNode labelNode = new LabelNode();
//                    labelInstructions = new ArrayList<>();
//                    labelInstructions.add(labelNode);
//                    MethodBlock methodBlock = new MethodBlock(labelNode.getLabel(), currentLabelNode.getLabel(), labelInstructions);
//                    graph.addMethodBlock(methodBlock);
//
//                    instructionToNewLabel.put(instruction, labelNode);
//                    instruction = instructionsIterator.next();
//                }
            }

//            labelInstructions.add(instruction);
        }

        instructions = methodNode.instructions;
        instructionsIterator = instructions.iterator();

        instruction = instructionsIterator.next();
        labelNode = (LabelNode) instruction;

        block = graph.getMethodBlock(labelNode);
        List<AbstractInsnNode> blockInstructions = block.getInstructions();
        blockInstructions.add(instruction);

        while (instructionsIterator.hasNext()) {
            instruction = instructionsIterator.next();
            instructionType = instruction.getType();

            if(instructionType == AbstractInsnNode.LABEL) {
                labelNode = (LabelNode) instruction;
                MethodBlock possibleBlock = graph.getMethodBlock(labelNode);

                if(possibleBlock != null) {
                    block = possibleBlock;
                    blockInstructions = block.getInstructions();
                }
            }

            blockInstructions.add(instruction);
//            else if(instructionType == AbstractInsnNode.JUMP_INSN) {
//                AbstractInsnNode nextInstruction = instruction.getNext();
//
//                if(nextInstruction.getType() == AbstractInsnNode.LABEL) {
//                    labelNode = (LabelNode) nextInstruction;
//                    block = graph.getMethodBlock(labelNode);
//                    blockInstructions = block.getInstructions();
//                }
//                else {
//                    throw new RuntimeException();
//                }
//            }

        }

        block = null;
        instructions = methodNode.instructions;
        instructionsIterator = instructions.iterator();

        while (instructionsIterator.hasNext()) {
            instruction = instructionsIterator.next();
            instructionType = instruction.getType();

            if(instructionType == AbstractInsnNode.LABEL) {
                labelNode = (LabelNode) instruction;
                MethodBlock possibleBlock = graph.getMethodBlock(labelNode);

                if(possibleBlock == null) {
                    continue;
                }

                if(block == null) {
                    block = possibleBlock;
                }
                else {
                    AbstractInsnNode previousInstruction = instruction.getPrevious();

                    if(previousInstruction.getType() != AbstractInsnNode.JUMP_INSN) {
                        if(!block.getSuccessors().contains(possibleBlock)) {
                            graph.addEdge(block, possibleBlock);
                        }
                    }

                    block = possibleBlock;
                }

            }
            else if(instructionType == AbstractInsnNode.JUMP_INSN) {
                JumpInsnNode jumpInsn = (JumpInsnNode) instruction;
                MethodBlock destinationBlock = new MethodBlock(jumpInsn.label);
                graph.addEdge(block, destinationBlock);

                if(jumpInsn.getOpcode() == Opcodes.GOTO) {
                    continue;
                }

                AbstractInsnNode nextInstruction = instruction.getNext();

                if(nextInstruction.getType() == AbstractInsnNode.LABEL) {
                    labelNode = (LabelNode) nextInstruction;
                    destinationBlock = graph.getMethodBlock(labelNode);
                    graph.addEdge(block, destinationBlock);
                }
                else {
                    throw new RuntimeException();
                }
            }
        }

        this.connectToEnterNode(graph);
        this.connectToExitNode(graph);



//
//            if(instructionType == AbstractInsnNode.LABEL) {
//                labelNode = (LabelNode) instruction;
//                MethodBlock possibleBlock = graph.getMethodBlock(labelNode);
//
//                if(possibleBlock != null) {
//                    if(block == null) {
//
//                        block = possibleBlock;
//                    }
//                    else {
//                        if(block.getSuccessors().contains(possibleBlock)) {
//                            block = possibleBlock;
//                        }
//                        else {
//                            System.out.println("nope");
//
//                        }
//
//                    }
//                }
//            }
//            else if(instructionType == AbstractInsnNode.JUMP_INSN) {
//                JumpInsnNode jumpInsn = (JumpInsnNode) instruction;
//                MethodBlock destinationBlock = new MethodBlock(jumpInsn.label);
//                graph.addEdge(block, destinationBlock);
//
//                if(jumpInsn.getOpcode() == Opcodes.GOTO) {
//                    continue;
//                }
//
//                AbstractInsnNode nextInstruction = instruction.getNext();
//
//                if(nextInstruction.getType() == AbstractInsnNode.LABEL) {
//                    labelNode = (LabelNode) nextInstruction;
//                    destinationBlock = graph.getMethodBlock(labelNode);
//                    graph.addEdge(block, destinationBlock);
//                }
//                else {
//                    throw new RuntimeException();
//                }
//            }


//        MethodBlock currentMethodBlock = null;
//        AbstractInsnNode previousInstruction = null;
//        Set<MethodBlock> blocksWithReturn = new HashSet<>();
//
//        while (instructionsIterator.hasNext()) {
//            AbstractInsnNode instruction = instructionsIterator.next();
//            int instructionType = instruction.getType();
//
//            if(instructionType == AbstractInsnNode.LABEL) {
//                LabelNode labelNode = (LabelNode) instruction;
//                MethodBlock successor = graph.getMethodBlock(labelNode.getLabel());
//
//                if(currentMethodBlock != null && (previousInstruction.getOpcode() < Opcodes.GOTO || previousInstruction.getOpcode() > Opcodes.RETURN) && previousInstruction.getOpcode() != Opcodes.ATHROW) {
//                    graph.addEdge(currentMethodBlock, successor);
//                }
//
//                if(currentMethodBlock != null) {
//                    for(TryCatchBlockNode tryCatchBlockNode : methodNode.tryCatchBlocks) {
//                        if(tryCatchBlockNode.end.getLabel().equals(currentMethodBlock.getOriginalLabel())) {
//                            if(tryCatchBlockNode.handler.getLabel().equals(successor.getOriginalLabel())) {
//                                graph.addEdge(currentMethodBlock, successor);
//                            }
//                        }
//                    }
//                }
//
//                currentMethodBlock = successor;
//            }
//            else if(instructionType == AbstractInsnNode.JUMP_INSN) {
//                JumpInsnNode jumpNode = (JumpInsnNode) instruction;
//                LabelNode labelNode = jumpNode.label;
//                MethodBlock successor = graph.getMethodBlock(labelNode.getLabel());
//                graph.addEdge(currentMethodBlock, successor);
//
//                AbstractInsnNode nextInstruction = instruction.getNext();
//
//                if(nextInstruction.getType() != AbstractInsnNode.LABEL) {
//                    labelNode = instructionToNewLabel.get(instruction);
//                    successor = graph.getMethodBlock(labelNode.getLabel());
//                    graph.addEdge(currentMethodBlock, successor);
//
//                    currentMethodBlock = successor;
//                }
//            }
//            else if(instructionType == AbstractInsnNode.TABLESWITCH_INSN) {
//                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) instruction;
//                MethodBlock successor = graph.getMethodBlock(tableSwitchInsnNode.dflt.getLabel());
//                graph.addEdge(currentMethodBlock, successor);
//
//                for(LabelNode labelNode : tableSwitchInsnNode.labels) {
//                    successor = graph.getMethodBlock(labelNode.getLabel());
//                    graph.addEdge(currentMethodBlock, successor);
//                }
//            }
//            else if(instructionType == AbstractInsnNode.LOOKUPSWITCH_INSN) {
//                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) instruction;
//                MethodBlock successor = graph.getMethodBlock(lookupSwitchInsnNode.dflt.getLabel());
//                graph.addEdge(currentMethodBlock, successor);
//
//                for(LabelNode labelNode : lookupSwitchInsnNode.labels) {
//                    successor = graph.getMethodBlock(labelNode.getLabel());
//                    graph.addEdge(currentMethodBlock, successor);
//                }
//            }
//            else if(instruction.getOpcode() == Opcodes.ATHROW || (instruction.getOpcode() >= Opcodes.GOTO && instruction.getOpcode() <= Opcodes.RETURN)) {
//                blocksWithReturn.add(currentMethodBlock);
//            }
//
//            previousInstruction = instruction;
//
//            for(MethodBlock blockWithReturn : blocksWithReturn) {
//                // TODO this is what makes the exit block to have an edge with itself
//                graph.addEdge(blockWithReturn, graph.getExitBlock());
//                blockWithReturn.setWithRet(true);
//            }
//        }

        return graph;
    }

    private void connectToEnterNode(MethodGraph graph) {
        AbstractInsnNode instruction = this.methodNode.instructions.getFirst();

        if(instruction.getType() != AbstractInsnNode.LABEL) {
            throw new RuntimeException();
        }

        LabelNode labelNode = (LabelNode) instruction;
        MethodBlock firstBlock = graph.getMethodBlock(labelNode);
        graph.addEdge(graph.getEntryBlock(), firstBlock);
    }

    public static void connectToExitNode(MethodGraph graph) {
        for(MethodBlock methodBlock : graph.getBlocks()) {
            for(AbstractInsnNode instruction : methodBlock.getInstructions()) {
                int opcode = instruction.getOpcode();

                if(opcode == Opcodes.RET || (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                    graph.addEdge(methodBlock, graph.getExitBlock());
                }
            }
        }
    }
}
