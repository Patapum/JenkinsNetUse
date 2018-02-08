def call(Map parameters = [:], body) {
	def disk = null
	try {
		def result = bat(script: 'net use * \\\\localhost\\%cd::=$%', returnStdout: true).trim()
		echo result
		disk = (result =~ /Drive (.*) is now connected to/)[0][1];
		dir("$disk\\") {
			body()
		}
	}
	finally {
		if (disk != null)
		{
			bat "net use $disk /delete /y"
		}
	}
}