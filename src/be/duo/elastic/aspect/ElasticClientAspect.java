package be.duo.elastic.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Component;

import be.duo.elastic.model.ElasticNode;

@Aspect
@Component
public class ElasticClientAspect {
	//TODO make an around aspect to provide and close Clients
	// or after... 
	
	@Around("execution(* be.duo.elastic.*Manager.*(..)) && args(...)")
	public void prepareClientAspect(ProceedingJoinPoint joinPoint){
		TransportClient client;
		try {
			client = new ElasticNode().getEsClient();
		
			joinPoint.proceed();
			
			client.close();
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
