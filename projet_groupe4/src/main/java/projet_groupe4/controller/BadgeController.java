package projet_groupe4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import projet_groupe4.model.Badge;
import projet_groupe4.service.BadgeService;

@Controller
@RequestMapping("/badge")
public class BadgeController {
	
	@Autowired
	private BadgeService srv;
	
	@GetMapping
	public String allBadge(Model model)
	{
		model.addAttribute("badges",this.srv.getAll());
		model.addAttribute("badge",new Badge());
		return "badges/badges";
	}

	@GetMapping("/{id}")
	public String ficheBadge(@PathVariable Integer id,Model model)
	{
		Badge badge = (Badge)this.srv.getById(id);
		model.addAttribute("badge",badge);
		return "badges/updateBadge";
	}

	@GetMapping("/delete/{id}")
	public String supprimerBadge(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
		return "redirect:/badge";
	}

	@PostMapping
	public String ajoutBadge(@ModelAttribute Badge badge)
	{
		this.srv.create(badge);
		return "redirect:/badge";
	}

	@PostMapping("/{id}")
	public String modifierBadge(@ModelAttribute Badge badge)
	{

		this.srv.update(badge);
		return "redirect:/badge";
	}
	

}
