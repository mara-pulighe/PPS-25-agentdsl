import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ModelReplyTest extends AnyFlatSpec with Matchers:

  "Una risposta di testo" should "contenere il testo del modello" in:
    val r: ModelReply.Text = ModelReply.Text("Piove, porta l'ombrello")
    r.text should be("Piove, porta l'ombrello")

  "Una richiesta di tool" should "conservare le richieste ricevute" in :
    val req = ToolRequest(None, "meteo", """{"città": "Cesena"}""")
    val r: ModelReply.ToolCalls = ModelReply.ToolCalls(List(req))
    r.requests should be(List(req))
