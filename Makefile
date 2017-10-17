HOSTNAME = ml.benjmann.net

default:
	cat Makefile

ansible:
	#ansible-playbook ansible.yml -i $(HOSTNAME), --private-key emr-gleenn.pem  --verbose --user glenn
	ansible-playbook ansible.yml -i $(HOSTNAME),  --private-key ~/.ssh/id_bens_server_rsa --verbose --user glenn
	#--limit @/Users/glenn/workspace/tensorflow/cortex-toy/ansible.retry

ssh:
	ssh ben

remote_repl:
	scp -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config -r ./project.clj ./src ./resources ./test glenn@ml.benjmann.net:
	ssh -F ~/.ssh/config -i ~/.ssh/id_bens_server_rsa glenn@ml.benjmann.net "lein deps"
#	ssh -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config ben "lein repl :headless :port 54545 & echo $! > lein.pid"
	ssh -i ~/.ssh/id_bens_server_rsa -F ~/.ssh/config ben "lein repl :headless :port 54545"

#kill_remote_repl:
#	ssh ben "killall lein"

tunnel:
	ssh -L 54545:localhost:54545 glenn@ben -N
