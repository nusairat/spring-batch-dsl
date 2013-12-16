
class SpringBatchBuilder {
  static include(Closure c) {
    println "INSIDE!"
    def clone = c.clone()
    clone.delegate = new SpringBatchBuilder()
    clone.resolveStrategy = Closure.DELEGATE_ONLY
    clone()
  }

  void batch(map, closure) {
    println "here :: $map"
  }

  def task(Closure cl, ) {

  }
}

SpringBatchBuilder.include {
  batch(id : 'myBatchJob') {
    task(id : 'myFirstTask') {
      myService.execute()
    } on 'NEXT' go to 'mySpecialTask',
    on 'MESSEDUP' go to 'myFirstTask',
    go to 'fnalTask'

    task(id : 'mySpecialTask') {
      read runThisStuff.run()
      write runThisStuff.run() every 20 commits
    } go to 'finalTask'

    split finalTask 10 times {
      myService.execute()
    }
  }
}


task('foo') {
   doSomething()
}.then {
  on_success: { },
  on_failure: { }, ....
}

batch('myBatchJob') {
  task('myFirstTask') {
      