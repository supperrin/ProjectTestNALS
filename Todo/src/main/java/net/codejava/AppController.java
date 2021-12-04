package net.codejava;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AppController {

	@Autowired
	private TodoService service; 
	
//	@RequestMapping("/")
//	public String viewHomePage(Model model) {
//		List<Todo> listTodos = service.listAll();
//		model.addAttribute("listTodos", listTodos);
//		
//		return "index";
//	}
	@GetMapping("/")
	public String home() {
		return "redirect:/todo";
	}
	
	@GetMapping("/todo")
	public String index(Model model,HttpServletRequest request
			,RedirectAttributes redirect) {
		request.getSession().setAttribute("listTodos", null);
		
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/todo/page/1";
	}
	
	@GetMapping("/todo/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, 
			@PathVariable int pageNumber, Model model, @RequestParam("field") Optional<String> field) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listTodos");
		int pagesize = 3;
		Sort sort = Sort.by(Direction.ASC, field.orElse("workname"));
		List<Todo> list =(List<Todo>) service.listAll(sort);
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("listTodos", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/todo/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("listTodos", pages);

		return "index";
	}

	@RequestMapping("/new")
	public String showNewTodoPage(Model model) {
		Todo todo = new Todo();
		model.addAttribute("todo", todo);
		
		return "new_todo";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveTodo(@ModelAttribute("todo") Todo todo) {
		service.save(todo);
		
//		return "redirect:/";
		return "redirect:/todo";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditTodoPage(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_todo");
		Todo todo = service.get(id);
		mav.addObject("todo", todo);
		
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteTodo(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";		
	}
	
}
