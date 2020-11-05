package com.example.ebank.repositories;

import com.example.ebank.models.Customer;
import com.example.ebank.utils.ProfileManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepository {

	private final JpaCustomerRepository jpaRepository;
	private final MockCustomerRepository mockRepository;
	private final ProfileManager profileManager;

	public CustomerRepository(JpaCustomerRepository jpaRepository,
			MockCustomerRepository mockRepository,
			ProfileManager profileManager) {
		this.jpaRepository = jpaRepository;
		this.mockRepository = mockRepository;
		this.profileManager = profileManager;
	}

	public List<Customer> findAll() {
		return profileManager.isMockProfileActive()
				? mockRepository.findAll()
				: jpaRepository.findAll();
	}

	public Optional<Customer> findById(Long id) {
		return profileManager.isMockProfileActive()
				? mockRepository.findById(id)
				: jpaRepository.findById(id);
	}

	public Optional<Customer> findByIdentityKey(String identityKey) {
		return profileManager.isMockProfileActive()
				? mockRepository.findByIdentityKey(identityKey)
				: jpaRepository.findByIdentityKey(identityKey);
	}
}
