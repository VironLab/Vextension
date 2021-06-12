pipeline {
    agent any

    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "repo.vironlab.eu"
        NEXUS_REPOSITORY = "maven-snapshot"
        NEXUS_CREDENTIAL_ID = "nexus"
        PROJECT_VERSION = "1.0.0-SNAPSHOT"
    }

    stages {
        stage("Clean") {
            steps {
                sh "chmod +x ./gradlew";
                sh "./gradlew clean";
            }
        }
        stage("Build") {
            steps {
                sh "./gradlew build";
            }
            post {
                success {
                    archiveArtifacts artifacts: 'vextension-common/build/libs/vextension-common.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-minecraft-server/build/libs/vextension-minecraft-server.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-minecraft-proxy/build/libs/vextension-minecraft-proxy.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-discord/build/libs/vextension-discord.jar', fingerprint: true
                }
            }
        }
        stage("Build ShadowJar") {
            steps {
                sh "./gradlew shadowJar";
            }
            post {
                success {
                    archiveArtifacts artifacts: 'vextension-common/build/libs/vextension-common-full.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-minecraft-server/build/libs/vextension-minecraft-server-full.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-minecraft-proxy/build/libs/vextension-minecraft-proxy-full.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-discord/build/libs/vextension-discord-full.jar', fingerprint: true
                }
            }
        }
        //stage("Docs") {
        //    steps {
        //        sh "./gradlew dokkaHtmlMultiModule";
        //        sh "rm -r /var/www/docs/vextension-v1.0.0"
        //        sh "mkdir /var/www/docs/vextension-v1.0.0"
        //        sh "cp -r build/vextension-v1.0.0 /var/www/docs/"
        //    }
        //}
        stage("Sources") {
            steps {
                sh "./gradlew sourcesJar";
            }
            post {
                success {
                    archiveArtifacts artifacts: 'vextension-common/build/libs/vextension-common-sources.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-minecraft-server/build/libs/vextension-minecraft-server-sources.jar', fingerprint: true
                    archiveArtifacts artifacts: 'vextension-discord/build/libs/vextension-discord-sources.jar', fingerprint: true
                }
            }
        }
        stage("Publish") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                  sh "./gradlew publish -DpublishPassword=$PASSWORD -DpublishName=$USERNAME"
                }
            }
        }
    }
}
