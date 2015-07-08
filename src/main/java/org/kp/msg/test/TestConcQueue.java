package org.kp.msg.test;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestConcQueue {
	public static void main(String[] args){
		Queue<String> cq = new ConcurrentLinkedQueue<String>();
		cq.add("egg");
		//cq.add("apple");
		
		Worker w1 = new Worker(cq,"tom");
		Worker w2 = new Worker(cq,"jerry");

	}
	
}

class Worker implements Runnable
{
   Thread mythread ;
   String name;
   Queue<String> cq = null;
   
   Worker(Queue<String> cq, String name)
   { 
	  this.cq = cq;
	  this.name = name;
      mythread = new Thread(this, "my runnable thread");
      System.out.println("my thread created" + mythread);
      mythread.start();
   }
   public void run()
   {
      try
      {
    	  for(int i = 0;i<5;i++){
        	  String content = cq.poll();
        	  while(content == null){
        		  content = cq.poll();
        	  }
        	  System.out.println(name + " is getting " + content);
        	  Random rand = new Random();
        	  Thread.sleep(rand.nextInt(1000));
        	  cq.add(content);
        	  System.out.println(content + " is returned by " + name);
        	  Thread.sleep(rand.nextInt(1000));
    	  }
     }
     catch(InterruptedException e)
     {
        System.out.println("my thread interrupted");
     }
     System.out.println("mythread run is over" );
   }
}