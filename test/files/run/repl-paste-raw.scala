
import scala.tools.partest.SessionTest

object Test extends SessionTest {
  override def session =
s"""|
    |scala> :paste $pastie
    |Pasting file $pastie...
    |
    |scala> val favoriteThing = brown_paper.Gift(true)
    |favoriteThing: brown_paper.Gift = Gift(true)
    |
    |scala> favoriteThing.hasString
    |res0: Boolean = true
    |
    |scala> :quit"""
  def pastie = testPath changeExtension "pastie"

  override def stripMargins: Boolean = true

  override def show() = checkSession()
}
