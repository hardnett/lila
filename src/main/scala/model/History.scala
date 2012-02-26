package lila
package model

case class History(
    lastMove: Option[(Pos, Pos)] = None,
    castles: Map[Color, (Boolean, Boolean)] = Map(
      White -> (true, true),
      Black -> (true, true)
    )) {

  def isLastMove(p1: Pos, p2: Pos) = lastMove == (p1, p2)

  def canCastle(color: Color) = new {
    def on(side: Side): Boolean = (colorCastles(color), side) match {
      case ((king, _), KingSide)   ⇒ king
      case ((_, queen), QueenSide) ⇒ queen
    }
    def any = on(KingSide) || on (QueenSide)
  }

  def withoutCastle(color: Color, side: Side) = copy(
    castles = castles updated (color, (colorCastles(color), side) match {
      case ((_, queen), KingSide) ⇒ (false, queen)
      case ((king, _), QueenSide) ⇒ (king, false)
    })
  )

  def withoutCastles(color: Color) = copy(
    castles = castles updated (color, (false, false))
  )

  private def colorCastles(color: Color) = castles get color getOrElse (true, true)
}

object History {

  def castle(color: Color, kingSide: Boolean, queenSide: Boolean) = History(
    castles = Map(color -> (kingSide, queenSide))
  )

  def noCastle = History(
    castles = Map.empty
  )
}