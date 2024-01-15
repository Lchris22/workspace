pipeline {
  agent {
    node {
      label 'docker-agent-alpine'
    }

  }
  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh '''
                ip addr

                docker -v
                ls
                

wget https://download.oracle.com/java/17/archive/jdk-17.0.7_linux-x64_bin.tar.gz
tar -xvf jdk-17.0.7_linux-x64_bin.tar.gz
mv jdk-17.0.7 /opt/

JAVA_HOME=\'/opt/jdk-17.0.7\'
PATH="$JAVA_HOME/bin:$PATH"
export PATH
java -version
                pwd
                cd backend
                docker build -t build_container .
                '''
      }
    }

    stage('Test') {
      steps {
        echo 'Testing..'
        sh '''
                cd myapp
                python3 hello.py
                python3 hello.py --name=Brad
                '''
      }
    }

    stage('Deliver') {
      steps {
        echo 'Deliver....'
        sh '''
                echo "doing delivery stuff.."
                '''
      }
    }

  }
  triggers {
    pollSCM('*/5 * * * *')
  }
}