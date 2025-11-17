package projet_groupe4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import projet_groupe4.service.AvisService;
import projet_groupe4.model.Avis;

@Controller
@RequestMapping("/avis")
public class AvisController {
	@Autowired
	private AvisService srv;
	
	@GetMapping
	public String allAvis(Model model)
	{
		model.addAttribute("avis",this.srv.getAll());
		model.addAttribute("avis",new Avis());
		return "avis/avis";
	}

	@GetMapping("/{id}")
	public String ficheAvis(@PathVariable Integer id,Model model)
	{
		Avis avis = (Avis)this.srv.getById(id);
		model.addAttribute("avis",avis);
		return "avis/updateAvis";
	}

	@GetMapping("/delete/{id}")
	public String supprimerAvis(@PathVariable Integer id)
	{
		this.srv.deleteById(id);
		return "redirect:/avis";
	}

	@PostMapping
	public String ajoutAvis(@ModelAttribute Avis avis)
	{
		this.srv.create(avis);
		return "redirect:/avis";
	}

	@PostMapping("/{id}")
	public String modifierAvis(@ModelAttribute Avis avis)
	{

		this.srv.update(avis);
		return "redirect:/avis";
	}
	
	
	

}
