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
		System.out.println("just entered MyUserDeailsService classe's loadByUsername method" );

		Optional<Customer> cst = customerRepository.findByEmail(username);


		if (cst.isPresent()) {

			Customer customer = cst.get();
			System.out.println("loadUserByUsername method user is customer");
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(customer.getRole());
			System.out.println(sga + " = new SimpleGrantedAuthority(customer.getRole())");
			authorities.add(sga);
			User user = new User(customer.getEmail(), customer.getPassword(), authorities);
			System.out.println( user  + " = new User(customer.getEmail(), customer.getPassword(), authorities");
			return user;

		}

		Optional<Manager> man = managerRepo.findByEmail(username);

		if (man.isPresent()) {

			Manager manager = man.get();
			System.out.println("loadUserByUsername method user is Manager");
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(manager.getRole());
			System.out.println(sga + " = new SimpleGrantedAuthority(manager.getRole())");
			authorities.add(sga);
			User user = new User(manager.getEmail(), manager.getPassword(), authorities);
			System.out.println( user  + " = new User(manager.getEmail(), manager.getPassword(), authorities");

			return user;

		}

		Optional<Admin> adm = adminRepo.findByEmail(username);

		if (adm.isPresent()) {

			Admin admin = adm.get();
			System.out.println("loadUserByUsername method user is Admin");
			List<GrantedAuthority> authorities = new ArrayList<>();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(admin.getRole());
			System.out.println(sga + " = new SimpleGrantedAuthority(admin.getRole())");
			authorities.add(sga);
			User user = new User(admin.getEmail(), admin.getPassword(), authorities);
			System.out.println( user  + " = new User(admin.getEmail(), admin.getPassword(), authorities)");

			return user;

		}

		throw new UsernameNotFoundException("User Details not found with this username: " + username);

	}

}
