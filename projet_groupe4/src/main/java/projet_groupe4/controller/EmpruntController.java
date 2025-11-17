package projet_groupe4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import projet_groupe4.model.Emprunt;
import projet_groupe4.service.EmpruntService;

@Controller
@RequestMapping("/emprunt")
public class EmpruntController {
	
	@Autowired
	private EmpruntService srv;
	
	@GetMapping
	public String allEmprunt(Model model)
	{
		model.addAttribute("emprunts",this.srv.getAll());
		model.addAttribute("emprunt",new Emprunt());
		return "emprunts/emprunts";
	}

	@GetMapping("/{id}")
	public String ficheEmprunt(@PathVariable Integer id,Model model)
	{
		Emprunt emprunt = (Emprunt)this.srv.getById(id);
		model.addAttribute("emprunt",emprunt);
		return "emprunts/updateEmprunt";
	}

	@GetMapping("/delete/{id}")
	public String supprimerEmprunt(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
		return "redirect:/emprunt";
	}

	@PostMapping
	public String ajoutEmprunt(@ModelAttribute Emprunt emprunt)
	{
		this.srv.create(emprunt);
		return "redirect:/emprunt";
	}

	@PostMapping("/{id}")
	public String modifierEmprunt(@ModelAttribute Emprunt emprunt)
	{

		this.srv.update(emprunt);
		return "redirect:/emprunt";
	}

}
