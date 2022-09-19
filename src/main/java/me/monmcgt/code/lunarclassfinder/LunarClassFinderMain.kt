package me.monmcgt.code.lunarclassfinder

import java.io.File

class LunarClassFinderMain {
    lateinit var lunarMapping: List<String>
    lateinit var mcpMapping: List<String>

    lateinit var lunarMappingFormat: List<MappingData>
    lateinit var mcpMappingFormat: List<MappingData>

    companion object {
        lateinit var LunarClassFinderMAIN: LunarClassFinderMain

        @JvmStatic
        fun main(args: Array<String>) {
            LunarClassFinderMAIN = LunarClassFinderMain()
        }
    }

    init {
        initFile()
        formatMapping()
//        writeNewMapping()
    }

    fun initFile(): Unit {
        /*lunarMapping = File("./mappings/lunar.txt")
        mcpMapping = File("./mappings/mcp.txt")*/

//        lunarMapping = this.javaClass.getResourceAsStream("/mappings/lunar.txt")?.bufferedReader()?.readLines() ?: throw Exception("Failed to read lunar.txt")
        lunarMapping = this.javaClass.getResourceAsStream("/patch/v1_8/mappings.txt")?.bufferedReader()?.readLines() ?: throw Exception("Failed to read lunar.txt")
        mcpMapping = this.javaClass.getResourceAsStream("/mappings/mcp.txt")?.bufferedReader()?.readLines() ?: throw Exception("Failed to read mcp.txt")
    }

    fun formatMapping(): Unit {
        lunarMappingFormat = lunarMapping/*.readLines()*/.map {
            val split = it.split(" ")
            MappingData(split[1], split[0])
        }
        mcpMappingFormat = mcpMapping/*.readLines()*/.filter {
            val split1 = it.split(":")
            split1[0] == "CL"
        }.map {
            val split = it.split(regex = ": ".toRegex(), limit = 2)[1].split(" ")
            MappingData(split[0], split[1])
        }
    }

    fun writeNewMapping(): Unit {
        val newMapping = getNewMapping()

//        val file = File("/home/mon/Downloads/Lunar Client Qt/Lunar Client Qt/mappings.txt")
        val file = File("./mappings/new.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(newMapping.joinToString("\n"))
    }

    fun getNewMapping(): List<String> {
        val newMapping = lunarMappingFormat.map {
            val realName = it.realName
            val mcpName = mcpMappingFormat.find { it2 -> it2.realName == realName }?.patchName ?: "NULL_NULL"
            "${it.realName} ${it.patchName} ${mcpName.replace("/", ".")}"
        }
        return newMapping
    }

    fun getPatchName(mcpName: String): String {
        val newMapping = getNewMapping()
//        println("lunarMapping.size = ${lunarMapping.size}")
        val firstOrNull = newMapping
            .firstOrNull {
                val replace = it.split(" ".toRegex(), 3)[2].replace('.', '/')
                val replace1 = mcpName.replace('.', '/')
//                println("replace = $replace")
//                println("replace1 = $replace1")
                replace == replace1
            }
//        println("firstOrNull ; $firstOrNull")
        val split = firstOrNull
            ?.split(" ".toRegex(), 3)
//        println("split ; $split")
//        println("split[1] ; ${split?.get(1)}")
        return split
            ?.get(1)
            ?.replace('.', '/')
            ?: return "NULL_NULL_NULL_NULL"
        /*return (getNewMapping().firstOrNull {
            val split = it.split(" ".toRegex(), 3)
            // find RenderGlobal
            split[2] == mcpName.replace("/", ".")
        } ?: *//*throw RuntimeException("Cannot find $mcpName class")*//* "NULL_NULL_NULL").split(" ".toRegex(), 3)[1].replace("/", ".")*/
    }

    fun getAllMinecraftClassesPatchName(): List<String> {
        return mcpMappingFormat.map { it.patchName }
    }
}