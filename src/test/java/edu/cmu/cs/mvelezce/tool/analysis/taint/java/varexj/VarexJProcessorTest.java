package edu.cmu.cs.mvelezce.tool.analysis.taint.java.varexj;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Created by mvelezce on 6/22/17.
 */
public class VarexJProcessorTest {

  @Test
  public void testParse()
      throws ParserConfigurationException, SAXException, IOException {
    VarexJProcessor.parse();
  }
}