//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

import org.gradle.internal.os.OperatingSystem

plugins {
	id("java-library")
	id("application")
	id("com.sandpolis.build.module")
	id("com.sandpolis.build.instance")
	id("com.sandpolis.build.publish")
	id("com.github.johnrengelman.shadow") version "7.1.0"
}

application {
	mainModule.set("com.sandpolis.installer")
	mainClass.set("com.sandpolis.installer.Main")
}

tasks.named<JavaExec>("run") {
	environment.put("S7S_DEVELOPMENT_MODE", "true")
	environment.put("S7S_LOG_LEVELS", "io.netty=WARN,java.util.prefs=OFF,com.sandpolis=TRACE")
}

tasks.withType<Jar>() {
	manifest {
		attributes["Application-Name"] = "Sandpolis"
		attributes["SplashScreen-Image"] = "image/logo.png"
	}
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.+")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.+")
	testImplementation("org.testfx:testfx-core:4.0.16-alpha")
	testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
	testImplementation("org.testfx:openjfx-monocle:jdk-12.0.1+2")
	testImplementation("org.awaitility:awaitility:4.0.1")

	// https://github.com/nayuki/QR-Code-generator
	implementation("io.nayuki:qrcodegen:1.7.0")

	if (project.getParent() == null) {
		implementation("com.sandpolis:core.foundation:+")
		implementation("com.sandpolis:core.integration.pacman:+")
	} else {
		implementation(project(":core:com.sandpolis.core.foundation"))
		implementation(project(":core:integration:com.sandpolis.core.integration.pacman"))
	}

	if (OperatingSystem.current().isMacOsX()) {
		implementation("org.openjfx:javafx-base:17:mac")
		implementation("org.openjfx:javafx-graphics:17:mac")
		implementation("org.openjfx:javafx-controls:17:mac")
		implementation("org.openjfx:javafx-fxml:17:mac")
		implementation("org.openjfx:javafx-web:17:mac")
	} else if (OperatingSystem.current().isLinux()) {
		implementation("org.openjfx:javafx-base:17:linux")
		implementation("org.openjfx:javafx-graphics:17:linux")
		implementation("org.openjfx:javafx-controls:17:linux")
		implementation("org.openjfx:javafx-fxml:17:linux")
		implementation("org.openjfx:javafx-web:17:linux")
	} else if (OperatingSystem.current().isWindows()) {
		implementation("org.openjfx:javafx-base:17:windows")
		implementation("org.openjfx:javafx-graphics:17:windows")
		implementation("org.openjfx:javafx-controls:17:windows")
		implementation("org.openjfx:javafx-fxml:17:windows")
		implementation("org.openjfx:javafx-web:17:windows")
	}
}

/*githubRelease {
	token = System.properties["publishing.github.token"]
	owner = "sandpolis"
	repo = "sandpolis"
	draft = true
	prerelease = true
}*/