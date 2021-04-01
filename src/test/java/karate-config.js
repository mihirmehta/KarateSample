function fn() {    
     
  karate.configure('report', { showLog: true , showAllSteps: false , showRequest: true , showResponse: true });
  karate.configure('retry',{ count:5 , interval:5000 });

}