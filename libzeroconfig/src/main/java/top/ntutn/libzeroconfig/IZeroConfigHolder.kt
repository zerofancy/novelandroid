package top.ntutn.libzeroconfig

interface IZeroConfigHolder {
    fun getValue(): Map<String, ZeroConfigInformation>
}