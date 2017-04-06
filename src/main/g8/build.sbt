lazy val api     = project                disablePlugins AssemblyPlugin settings ( libraryDependencies ++= L.api  )
lazy val core    = project dependsOn api  disablePlugins AssemblyPlugin settings ( libraryDependencies ++= L.core )
lazy val http    = project dependsOn core disablePlugins AssemblyPlugin settings ( libraryDependencies ++= L.http )
lazy val cli     = project dependsOn core disablePlugins AssemblyPlugin settings ( libraryDependencies ++= L.cli  )

lazy val bench   = project dependsOn http  enablePlugins  JmhPlugin      settings ( generatorType := "asm"          )
lazy val tests   = project dependsOn bench disablePlugins AssemblyPlugin settings ( libraryDependencies ++= L.tests )

lazy val parent = project in file(".") dependsOn cli aggregate (api, cli, core, http, tests) settings(
  assemblyJarName := "$name$-" + version.value + ".jar",
  aggregate in assembly := false
)
addCommandAlias("build", ";package;assembly")
addCommandAlias("travis", ";clean;coverage;testOnly -- xonly exclude contrib;coverageReport;coverageAggregate")
