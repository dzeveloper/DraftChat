package com.DraftChat

import org.scalatra.test.specs2._
import org.specs2.specification.core.SpecStructure

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class IndexServletSpec extends ScalatraSpec { def is: SpecStructure =
  "GET / on IndexServlet"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[IndexServlet], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
