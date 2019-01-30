package com.yoppworks.internal.instrumentationDemo.baseApp

import akka.actor.{ActorSystem, Props}
import com.yoppworks.internal.instrumentationDemo.baseApp.actors.ScheduledActor
import com.yoppworks.internal.instrumentationDemo.baseApp.messages.ScheduledMessage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object MyMainClass {
  def main(args: Array[String]): Unit = {
    println("Starting the application. Please press ENTER to close the app.")

    val system = ActorSystem("MyDemoApp")
    import system.scheduler

    val actorReference = system.actorOf(Props[ScheduledActor])

    val cancellable = scheduler.schedule(0 milliseconds, 1 second, actorReference, ScheduledMessage)

    scala.io.StdIn.readLine()
    cancellable.cancel()
    system.terminate()
  }
}
