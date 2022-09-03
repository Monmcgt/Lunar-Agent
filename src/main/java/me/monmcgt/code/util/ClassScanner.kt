package me.monmcgt.code.util

import me.monmcgt.code.Debug
import java.io.File

object ClassScanner {
    private val classLoader = Thread.currentThread().contextClassLoader

    fun getAllClasses(packageName: String = ""): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        val classPath = classLoader.getResource(packageName.replace(".", "/"))?.path
        Debug.println("CLASSPATH = null? ${classPath == null}")
        Debug.println("File(classPath) = ${File(classPath)}")
        Debug.println("File(classPath).listFiles() = ${File(classPath).listFiles()}")
        val classFiles = classPath?.let { File(it).listFiles() }
        Debug.println("CLASSFILES = null? ${classFiles == null}")
        classFiles?.forEach {
            Debug.println(" CLASSFILE > ${it.name}")
        }
        for (file in classFiles ?: emptyArray()) {
            if (file.isFile && file.name.endsWith(".class")) {
                val className = file.name.substring(0, file.name.length - 6)
                try {
                    classes.add(Class.forName(if (packageName.isEmpty()) className else "$packageName.$className"))
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            } else if (file.isDirectory) {
                classes.addAll(getAllClasses(if (packageName.isEmpty()) file.name else "$packageName.${file.name}"))
            } else {
                // file but not a class file
            }
        }
        return classes
    }
}