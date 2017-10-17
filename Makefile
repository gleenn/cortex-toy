HOSTNAME = ml.benjmann.net

ansible:
	#ansible-playbook ansible.yml -i $(HOSTNAME), --private-key emr-gleenn.pem  --verbose --user glenn
	ansible-playbook ansible.yml -i $(HOSTNAME),  --private-key ~/.ssh/id_bens_server_rsa --verbose --user glenn
	#--limit @/Users/glenn/workspace/tensorflow/cortex-toy/ansible.retry
