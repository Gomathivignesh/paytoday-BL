//package com.example.paytoday.dao.service;
//
//import com.example.paytoday.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//@Service(value = "UserDetailsServiceImpl")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private RetailerDAO retailerDAO;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//		User user = retailerDAO.getUserbyEmail(userId);
//		if(user == null){
//			throw new UsernameNotFoundException("Invalid username or password.");
//		}
//		return new org.springframework.security.core.userdetails.User(user.getName(), passwordEncoder.encode(user.getPassword()), getAuthority());
//	}
//
//	private List<SimpleGrantedAuthority> getAuthority() {
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_RETAILER"));
//	}
//
//}
