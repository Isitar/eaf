@echo off
set list=usermgt moviemgmt rentalmgmt
(for %%p in (%list%) do (
   (for %%j in (%%p\build\libs\*.jar) do (
		start cmd /k title %%p^&color 0A^&java -jar %%j
	))
)) 