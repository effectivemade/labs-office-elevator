package band.effective.office.workTogether

interface WorkTogether {
    fun getAll(): List<Teammate>
    fun getProperty(name: String): Map<String, String?>
}