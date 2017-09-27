package com.pseudo.tdd

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.scalatest.{FunSuite, Matchers}

case class Customer[T](name: String, address: String, metadata: T)

case class Privileged(desc: String)

class ObjectMapperSpecs extends FunSuite with Matchers {

  test("deserialises to case class") {

    val objectMapper = new ObjectMapper()
      .registerModule(DefaultScalaModule)

    val value1 = new TypeReference[Customer[Privileged]] {}

    val response = objectMapper.readValue[Customer[Privileged]](
      """{
           "name": "prayagupd",
           "address": "myaddress",
           "metadata": { "desc" : "some description" }
         }
      """.stripMargin, new TypeReference[Customer[Privileged]] {})

    response.metadata.getClass shouldBe classOf[Privileged]
    response.metadata.desc shouldBe "some description"
  }

}
