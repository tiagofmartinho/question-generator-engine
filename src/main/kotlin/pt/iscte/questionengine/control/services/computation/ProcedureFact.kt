package pt.iscte.questionengine.control.services.computation

/**
 * Represents a fact about a procedure that can be used to determine which questions should be asked about that procedure
 */

class ProcedureFact(val factType: FactType, val fact: Any?) {}
