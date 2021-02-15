properties([pipelineTriggers([githubPush()])])
parameters{
    string(name:"BRANCH", default:"final_project")
    string(name:"REMOTE", default:"final_project")
}
node {
    stage('Checkout external proj') {
        git branch: "${params.BRANCH}",
                url: 'https://github.com/ZakharE/JavaQaSeptember2020.git'
    }

    stage('Run tests') {
        catchError {
            withMaven(jdk: '', maven: 'maven') {
                sh "mvn clean test"+
                        "-Dselenide.remote=${params.REMOTE}" +
                        "-Dselenide.browser=chrome" +
                        "-Dselenide.browserSize=1920x1080" +
                        "-Dselenide.enableVnc=true" +
                        "-Dselenide.enableVideo=true"
            }
        }
    }

    stage('Generate report') {
        withMaven(jdk: '', maven: 'maven') {
            sh "mvn allure:report"
        }
    }

    stage('Publish') {
        echo 'Publish Allure report'
        allure([
                includeProperties: false,
                jdk              : '',
                properties       : [],
                reportBuildPolicy: 'ALWAYS',
                results          : [[path: 'target/allure-results']]
        ])
    }

    stage('Notify slack') {
        def stats = readJSON file: "${env.WORKSPACE}/target/site/allure-maven-plugin/widgets/summary.json"
        def skipped = stats["statistic"]["Skipped"] == null ? 0: stats["statistic"]["Skipped"]
        slackSend channel: 'slack_build_notify',
                message: "${env.JOB_NAME} Build # ${env.BUILD_NUMBER} - ${currentBuild.currentResult}\n".replace('and counting', '') +
                        "Passed: ${stats["statistic"]["passed"]}\n" +
                        "Failed: ${stats["statistic"]["failed"]}\n" +
                        "Skipped: ${skipped}\n " +
                        "Duration time: ${currentBuild.durationString}\n" +
                        "Branch name: ${params.BRANCH}"
    }

    stage("Archive artifacts") {
        archiveArtifacts artifacts: '**/target/', fingerprints: true
    }
}