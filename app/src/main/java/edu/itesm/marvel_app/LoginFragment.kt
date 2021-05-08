package edu.itesm.marvel_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.itesm.marvel_app.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var bind: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        bind = binding


        setLoginRegister()
    }


    private fun setLoginRegister(){

        bind.registerbtn.setOnClickListener {

            if (bind.correo.text.isNotEmpty() && bind.password.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(

                    bind.correo.text.toString(),
                    bind.password.text.toString()

                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        usuarioCreado()
                        bind.correo.text.clear()
                        bind.password.text.clear()
                    }
                }.addOnFailureListener{
                    Toast.makeText(getActivity(),it.toString(), Toast.LENGTH_LONG).show()

                }


            }

        }

        bind.loginbtn.setOnClickListener {
            if (bind.correo.text.isNotEmpty() && bind.password.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    bind.correo.text.toString(),
                    bind.password.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(getActivity(),"Bienvenido!",
                            Toast.LENGTH_LONG).show()
                            view?.findNavController()?.navigate(R.id.action_loginFragment2_to_startFragment2)

                    }else{
                        Toast.makeText(getActivity(),"Error en los datos!",
                            Toast.LENGTH_LONG).show()
                    }
                }


            }

        }

    }

    private fun usuarioCreado(){
        val builder = AlertDialog.Builder(requireActivity())
        with(builder){
            setTitle("usuario marvel")
            setMessage("Usuario creado con éxito!")
            setPositiveButton("Ok",null)
            show()
        }
    }

}