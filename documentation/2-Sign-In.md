[back](../2-Documentation.md)

## 2.2. Sign In


Once you sign up for the system, you can use your username and password to sign in.
After a successful sign-up, the system responds with an alphanumeric sequence that is used to authenticate future requests to the application.
Below are corresponding examples of a request and a response.

```shell
curl --request POST \
  --url http://localhost:8080/v1/home/sign-in \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.2.0' \
  --cookie JSESSIONID=1B4DF5F1DDC6972C1602627780928BFD \
  --data '{
	"username": "a.username",
	"password": "aP*ssw0rd"
}'
```

The alphanumeric sequence, or token, is the value of the `data` attribute. 
You should extract it to use in requests that require authentication.

```json
{
	"timestamp": "2026-01-28T17:28:26.091098800Z",
	"status": "200 OK",
	"message": null,
	"data": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOjE3Njk2MjEzMDU5OTEsInN1YiI6ImEudXNlcm5hbWUiLCJleHAiOjE3Njk2MjEzMTU5OTF9.pO1vOY9UuPpI8ZRz8v9VrLyJ-VqOnK1tWMyWVwP-xrDysBqxXipLcRPa7f2p7tFauRtWhbbNfAUeX8SJCB38mw"
}
```

If you receive a response from the application that's different from the one shown above, check the username and the password used to log in.
You can find more details about the system's behavior in the [3.2. Sign-in](../features/2-Sign-In.md).