package vacant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SidebarControl {

	@RequestMapping(path="/sidebar_1")
	@ResponseBody
	public Object sidebar1() {
		String s = "<div class=\"accordion\" fillSpace=\"sideBar\">\n" + 
				"	<div class=\"accordionHeader\">\n" + 
				"		<h2><span>Folder</span>界面组件</h2>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionContent\">\n" + 
				"		<ul class=\"tree treeFolder\">\n" + 
				"			<li><a href=\"tabsPage.html\" target=\"navTab\">框架面板</a></li>\n" + 
				"		</ul>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionHeader\">\n" + 
				"		<h2><span>Folder</span>典型页面</h2>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionContent\">\n" + 
				"		<ul class=\"tree treeFolder treeCheck\">\n" + 
				"			<li><a href=\"demo_upload.html\" target=\"navTab\" rel=\"demo_upload\">文件上传表单提交示例</a></li>\n" + 
				"			<li><a href=\"demo_page1.html\" target=\"navTab\" rel=\"demo_page1\">查询我的客户</a></li>\n" + 
				"			<li><a href=\"demo_page1.html\" target=\"navTab\" rel=\"demo_page2\">表单查询页面</a></li>\n" + 
				"			<li><a href=\"demo_page4.html\" target=\"navTab\" rel=\"demo_page4\">表单录入页面</a></li>\n" + 
				"			<li><a href=\"demo_page5.html\" target=\"navTab\" rel=\"demo_page5\">有文本输入的表单</a></li>\n" + 
				"		</ul>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionHeader\">\n" + 
				"		<h2><span>Folder</span>流程演示</h2>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionContent\">\n" + 
				"		<ul class=\"tree\">\n" + 
				"			<li><a href=\"newPage1.html\" target=\"dialog\" rel=\"dlg_page\">列表</a></li>\n" + 
				"		</ul>\n" + 
				"	</div>\n" + 
				"</div>";
		
		return s;
	}
	
	@RequestMapping(path="/sidebar_2")
	@ResponseBody
	public Object sidebar2() {
		String s = "<div class=\"accordion\" fillSpace=\"sideBar\">\n" + 
				"	<div class=\"accordionHeader\">\n" + 
				"		<h2><span>Folder</span>典型页面</h2>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionContent\">\n" + 
				"		<ul class=\"tree treeFolder treeCheck\">\n" + 
				"			<li><a href=\"demo_upload.html\" target=\"navTab\" rel=\"demo_upload\">文件上传表单提交示例</a></li>\n" + 
				"			<li><a href=\"demo_page1.html\" target=\"navTab\" rel=\"demo_page1\">查询我的客户</a></li>\n" + 
				"			<li><a href=\"demo_page1.html\" target=\"navTab\" rel=\"demo_page2\">表单查询页面</a></li>\n" + 
				"			<li><a href=\"demo_page4.html\" target=\"navTab\" rel=\"demo_page4\">表单录入页面</a></li>\n" + 
				"			<li><a href=\"demo_page5.html\" target=\"navTab\" rel=\"demo_page5\">有文本输入的表单</a></li>\n" + 
				"		</ul>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionHeader\">\n" + 
				"		<h2><span>Folder</span>流程演示</h2>\n" + 
				"	</div>\n" + 
				"	<div class=\"accordionContent\">\n" + 
				"		<ul class=\"tree\">\n" + 
				"			<li><a href=\"newPage1.html\" target=\"dialog\" rel=\"dlg_page\">列表</a></li>\n" + 
				"		</ul>\n" + 
				"	</div>\n" + 
				"</div>\n" + 
				"";
		return s;
	}
}
