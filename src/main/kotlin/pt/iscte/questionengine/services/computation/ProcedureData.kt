package pt.iscte.questionengine.services.computation

import pt.iscte.paddle.model.IProcedure

class ProcedureData(val procedure: IProcedure, val elements: Collection<ProcedureElement>, val facts: Collection<ProcedureFact>) {}
