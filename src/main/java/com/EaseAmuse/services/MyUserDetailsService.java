package com.EaseAmuse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EaseAmuse.models.Admin;
import com.EaseAmuse.models.Customer;
import com.EaseAmuse.models.Manager;
import com.EaseAmuse.repositories.AdminRepo;
import com.EaseAmuse.repositories.CustomerRepo;
import com.EaseAmuse.repositories.ManagerRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepo customerRepository;

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private ManagerRepo managerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Customer> cst = customerRepository.findByEmail(username);

//System.out.println(cst.get().getRole());

		if (cst.isPresent()) {

			Customer customer = cst.get();

			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(customer.getRole());
			System.out.println(sga);
			authorities.add(sga);

			return new User(customer.getEmail(), customer.getPassword(), authorities);

		}

		Optional<Manager> man = managerRepo.findByEmail(username);

		if (man.isPresent()) {

			Manager manager = man.get();

			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(manager.getRole());
			authorities.add(sga);

			return new User(manager.getEmail(), manager.getPassword(), authorities);

		}

		Optional<Admin> adm = adminRepo.findByEmail(username);

		if (adm.isPresent()) {

			Admin admin = adm.get();

			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(admin.getRole());
			authorities.add(sga);

			return new User(admin.getEmail(), admin.getPassword(), authorities);

		}

		throw new UsernameNotFoundException("User Details not found with this username: " + username);

	}

}
