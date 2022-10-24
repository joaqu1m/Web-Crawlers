package banco

data class dataComponente (
    var idComponente: Int,
    var nome: String
) {
    constructor() : this(0,"")
}

data class dataSecao (
    var idSecao: Int,
    var nome: String,
    var fkSecao: Int,
    var fkComponente: Int
) {
    constructor() : this(0,"", 0, 0)
}