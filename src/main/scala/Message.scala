enum Message:
  case User(text: String)
  case System(text: String)
  case Ai(text: String)