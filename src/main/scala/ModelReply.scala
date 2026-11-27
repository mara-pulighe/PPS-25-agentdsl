case class ToolRequest(id: Option[String], name: String, arguments: String)

enum ModelReply:
  case Text(text: String)
  case ToolCalls(requests: List[ToolRequest])