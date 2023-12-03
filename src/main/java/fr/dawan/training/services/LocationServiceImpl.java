package fr.dawan.training.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.dawan.training.dto.LocationDto;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LocationServiceImpl implements ILocationService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public List<LocationDto> importFromIntranet() throws Exception {
		ResponseEntity<LocationDto[]> resp = restTemplate.getForEntity("https://dawan.org/public/location/", LocationDto[].class);
		if(resp.getStatusCode()==HttpStatus.OK && resp.hasBody()) {
			LocationDto[] locations = resp.getBody();
			
			//faire un traitement particulier avec les données reçues
			
			return Arrays.asList(locations);
		}
		return null;
	}

	
}
