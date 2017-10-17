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
	ssh ben "lein repl :headless :port 54545 &" &

tunnel:
	ssh -L 54545:localhost:54545 glenn@ben -N
