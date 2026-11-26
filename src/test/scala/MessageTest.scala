import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MessageTest extends AnyFlatSpec with Matchers:

  "Un messaggio utente" should "contenere il testo scritto dall'utente" in:
    val m: Message.User = Message.User("Che tempo fa a Cesena?")

    m.text should be("Che tempo fa a Cesena?")

  "Un messaggio di sistema" should "contenere le istruzioni" in:
    val ms: Message.System = Message.System("Sei un assistente meteorologico")

    ms.text should be("Sei un assistente meteorologico")

  "Un messaggio dell'assistente" should "contenere la risposta del modello" in :
    val ma: Message.Ai = Message.Ai("Ciao, come posso aiutarti?")

    ma.text should be("Ciao, come posso aiutarti?")
    
  