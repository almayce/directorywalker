package utils

import java.io.File
import java.io.FileNotFoundException

class DirectoryWalker(private var root: String?) {

    var walkerFile = File(javaClass.protectionDomain.codeSource.location.file).parentFile

    public constructor() : this(null)

    fun parentDir() : DirectoryWalker {
        walkerFile = walkerFile.parentFile
        return this
    }

    fun findByRelativePath(relativePath: String): File {

        root?.let { root = replaceSeparator(root!!) }
        val separatedEndOfPath = replaceSeparator(relativePath)

        val rootDir = walkerFile.path + (root ?: "")

        File(rootDir).walkTopDown().forEach {
            if (it.path.contains(separatedEndOfPath))
                return it
        }

        throw FileNotFoundException(
            "File not found!\n" +
                    "Root Directory: $rootDir\n" +
                    "Target: $separatedEndOfPath"
        )
    }

    private fun replaceSeparator(path: String) =
        path.replace("/", System.getProperty("file.separator")).replace("\\\\", "\\")
}