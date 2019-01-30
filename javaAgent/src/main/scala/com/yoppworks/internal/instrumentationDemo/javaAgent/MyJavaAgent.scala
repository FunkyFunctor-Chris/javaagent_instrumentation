package com.yoppworks.internal.instrumentationDemo.javaAgent

import java.lang.instrument.Instrumentation

object MyJavaAgent {
  def premain(agentArgs: String, inst: Instrumentation): Unit = {
    System.err.println(">>> Loading up java agent!")
    System.err.println(s">>> Parameters passed to the java agent: '$agentArgs'")
    inst.addTransformer(new Transformer())
  }
}
