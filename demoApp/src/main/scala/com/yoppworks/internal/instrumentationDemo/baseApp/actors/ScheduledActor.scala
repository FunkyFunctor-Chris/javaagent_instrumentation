package com.yoppworks.internal.instrumentationDemo.baseApp.actors

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor
import com.yoppworks.internal.instrumentationDemo.baseApp.messages.ScheduledMessage

class ScheduledActor extends Actor{
  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

  override def receive: Receive = {
    case ScheduledMessage =>
      println(s"Scheduled message received at ${getCurrentTime(dateFormat)}")
  }

  def getCurrentTime(dateFormat: SimpleDateFormat): String = {
    dateFormat.format(new Date())
  }
}
