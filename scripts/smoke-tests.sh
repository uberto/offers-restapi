#greetings
curl localhost:8080

#create all valid offers
curl http://localhost:8080/offers

#create an offer
curl http://localhost:8080/offer -d @offer.json --header "Content-Type: application/json"

#read an offer
curl localhost:8080/offer?stock=IBM.N

#update an offer
curl http://localhost:8080/offer -d @buy_ibm.json --header "Content-Type: application/json"

#cancel an offer
curl localhost:8080/offer

