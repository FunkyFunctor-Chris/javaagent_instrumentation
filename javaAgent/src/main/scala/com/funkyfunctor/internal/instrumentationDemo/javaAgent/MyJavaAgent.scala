package com.funkyfunctor.internal.instrumentationDemo.javaAgent

import java.lang.instrument.Instrumentation

object MyJavaAgent {
  def premain(agentArgs: String, inst: Instrumentation): Unit = {
    printMessage("Loading up java agent!")
    printMessage(s"Parameters passed to the java agent: '$agentArgs'")
    inst.addTransformer(new Transformer())
  }

  def printMessage(msg: String): Unit = System.err.println(s">>> $msg")
}
