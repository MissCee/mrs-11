pipeline {
    agent any
    tools {
    	maven 'Maven 3.6'
        jdk 'JDK11'
    }
    stages {
    	stage('Version') {
    	    
    	    steps {
    	    	echo 'NOTE: this is the maven version of a docker container'
    	        sh 'mvn --version'
    	    }
    	}
    	stage('Checkout') {
        	steps {
            	checkout scm
        		sh 'mvn clean'
			}
		}
        stage('Build') {
        	steps {
        		sh 'mvn verify'
      		}
	}
	stage('Unit Test') {
            steps {
               echo 'Unit Testing...'
               sh 'mvn resources:testResources'
               sh 'mvn compiler:testCompile'
               sh 'mvn surefire:test'
               }
        }
        stage('Integration Testing') {
            steps {
                echo 'Integration Testing...'
            	sh 'mvn failsafe:integration-test'
      		}
        }

	stage('Site') {
	     steps {
		 sh 'mvn site'
	     }
	}
   }
	post {
	    always {
	        junit 'target/surefire-reports/**/*.xml'
	        junit 'target/failsafe-reports/**/*.xml'
	    }

	}

}
