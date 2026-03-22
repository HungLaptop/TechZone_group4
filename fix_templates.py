import pathlib, re
base = pathlib.Path('src/main/resources/templates')
for p in base.rglob('*.html'):
    text = p.read_text(encoding='utf-8')
    new_text = re.sub(r'<html\s+xmlns:th="http://www.thymeleaf.org"\s+th:replace="layout :: \(title=\'[^\']*\'\)">',
                      '<html xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{layout}">',
                      text)
    new_text = new_text.replace('th:fragment="content"', 'layout:fragment="content"')
    if new_text != text:
        print('updated', p)
        p.write_text(new_text, encoding='utf-8')
print('done')