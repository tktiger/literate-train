pipeline {
	agent any
	
	stages {
		stage("Checkout"){
			steps{
				checkout scm
			}
		}
		stage("Setup"){
			steps{
				sh """
					java --version
					mvn --version
					git --version
				"""
			}
		}
		stage("Compile"){
			steps{
				sh "mvn clean compile"		
			}
		}
		stage("Unit Tests"){
			steps{
				sh "mvn test"
			}
			post{
				always{
					junit "target/surefire-reports/*.xml"
				}
			}
		}
		stage("Coverage Report"){
			steps{
				sh "mvn jacoco:report"
			}
			post{
				always{
					archiveArtifacts artifacts: "target/site/jacoco/jacoco.xml",allowEmptyArchive: true
				}
			}
		}
		stage("Static Analysis"){
			steps{
				sh "mvn checkstyle:checkstyle pmd:pmd spotbugs:spotbugs"
			}
			post{
				always{
					archiveArtifacts artifacts: "target/site/**,target/spotbugs.xml",allowEmptyArchive: true
				}
			}
		}
		//stage("Sonar"){
		//	steps{
		//		withSonarQubeEnv('sonarqube'){
		//			sh "mvn sonar:sonar"
		//		}
		//	}
		//}
	}
}
