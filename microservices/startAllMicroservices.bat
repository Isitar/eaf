@echo off
set list=registry usermgt moviemgmt rentalmgmt 
(for %%p in (%list%) do (
   (for %%j in (%%p\build\libs\*.jar) do (
		start cmd /k title %%p^&color 0A^&java -jar %%j^&exit
		ping 127.0.0.1 /n 3
	))
)) 


echo @Value(value="$microservice.usermanagement:usermanagement}")

echo microservice.usermanagement: localhost:9090