<h1>Descomposition LU Algorithm in Kotlin</h1>
<h2>How to install Kotlin by bash (terminal - Ubuntu)</h2>
<ol>
    <li>Open a terminal</li>
    <li>Update the package index with the following command:
        <p><b>sudo apt update</b></p>
    </li>
    <li>Install the kotlin package with the following command:
        <p><b>sudo apt install kotlin</b></p>
    </li>
    <li>Once the installation is complete, you can verify that Kotlin has been installed with the following command:
        <p><b>kotlinc -version</b></p>
    </li>
</ol>
<h2>How to compile a Kotlin file</h2>
<ol>
    <li>Open a terminal</li>
    <li>Navigate to the directory containing your Kotlin file (.kt)</li>
    <li>Use the Kotlin compiler (<b>kotlinc</b>) to compile your Kotlin file with the following command:
        <p><b>kotlinc MyFile.kt -include-runtime -d MyFile.jar</b></p>
    </li>
    <li>Once the compilation command completes without errors, you can run the file with the following command:
        <p><b>java -jar MyFile.jar</b></p>
    </li>
</ol>
