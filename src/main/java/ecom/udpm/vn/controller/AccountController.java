package ecom.udpm.vn.controller;

import ecom.udpm.vn.dto.response.Account.AccountDTO;
import ecom.udpm.vn.entity.Account;
import ecom.udpm.vn.service.impl.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/account")
@CrossOrigin
@PreAuthorize("hasAuthority('admin')")
@AllArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@GetMapping("/findAll")
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(accountService.getAll());
	}

	@GetMapping()
	public ResponseEntity<Object> getAccount(@RequestParam Integer id) {
		return ResponseEntity.ok(accountService.getAllDetails(id));
	}

	@GetMapping("{page}")
	public ResponseEntity<Object> getPerPage(@PathVariable("page") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		Map<String, Object> result = new HashMap<>();
		Page<Account> accounts = accountService.getPerPage(size, page);
		result.put("data", accounts.getContent());
		result.put("total", accounts.getTotalElements());
		result.put("from", accounts.getSize() * accounts.getNumber() + 1);
		result.put("to", accounts.getSize() * accounts.getNumber() + accounts.getNumberOfElements());
		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(accountService.save(accountDTO));
	}

	@PatchMapping
	public ResponseEntity<Object> editAccount(@Valid @RequestBody AccountDTO accountDTO) {
		return ResponseEntity.ok(accountService.edit(accountDTO));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		accountService.delete(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
