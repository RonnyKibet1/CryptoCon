package crypto_coin_converter.blogspot.codedeveloped.com.cryptocon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import com.jaredrummler.materialspinner.MaterialSpinner
import com.squareup.picasso.Picasso
import crypto_coin_converter.blogspot.codedeveloped.com.cryptocon.model.Coin
import crypto_coin_converter.blogspot.codedeveloped.com.cryptocon.remote.CoinService
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var coinService: CoinService? = null
    lateinit var coinToCoin: RadioButton
    lateinit var moneyToCoin: RadioButton
    lateinit var coinToMoney: RadioButton
    lateinit var radio_group: RadioGroup
    lateinit var fromSpinner: MaterialSpinner
    lateinit var toSpinner: MaterialSpinner
    lateinit var convertButton: Button
    lateinit var coinImage: ImageView
    lateinit var convertedValueTxtView: TextView

    private val money = arrayOf("USD", "EUR", "GBP")
    private val coin = arrayOf("BTC", "ETH", "ETC", "XRP", "LTC", "XMR", "DASH", "MAID", "AUR", "XEM")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coinService = Common.getCoinService()

        fromSpinner = findViewById(R.id.fromSpinner)
        toSpinner = findViewById(R.id.toSpinner)
        radio_group = findViewById(R.id.radio_group)
        coinToCoin  = findViewById(R.id.radioButton_coin_to_coin)
        moneyToCoin  = findViewById(R.id.radioButton_money_to_coin)
        coinToMoney  = findViewById(R.id.radioButton_coin_to_money)
        convertButton = findViewById(R.id.convertButton)
        coinImage = findViewById(R.id.imageView_convert_element)
        convertedValueTxtView = findViewById(R.id.textView_convert_value)

        val coinAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, coin)
        val moneyAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, money)

        //listen to button convert click
        convertButton.setOnClickListener {
            if(toSpinner.getItems<String>() != null && fromSpinner.getItems<String>()!= null){
                calculateValue()
            }else{
                Toast.makeText(this, "Please select conversion type from options above", Toast.LENGTH_SHORT).show()
            }

        }

        //radio group select
        radio_group.setOnCheckedChangeListener { group, checkedId ->
             if(checkedId == R.id.radioButton_coin_to_coin){
                coinToCoin(coinAdapter, moneyAdapter)
             }else if(checkedId == R.id.radioButton_money_to_coin){
                 moneyToCoin(coinAdapter, moneyAdapter)
             }else if(checkedId == R.id.radioButton_coin_to_money){
                 coinToMoney(coinAdapter, moneyAdapter)
             }

        }

        loadCoinList(coinAdapter, moneyAdapter)
    }

    private fun loadCoinList(coinAdapter: ArrayAdapter<String>, moneyAdapter: ArrayAdapter<String>){
        if(coinToMoney.isSelected){
            coinToMoney(coinAdapter, moneyAdapter)
        }else if(coinToCoin.isSelected){
            coinToCoin(coinAdapter, moneyAdapter)
        }else if(moneyToCoin.isSelected){
            moneyToCoin(coinAdapter, moneyAdapter)
        }
    }

    private fun coinToCoin(coinAdapter: ArrayAdapter<String>, moneyAdapter: ArrayAdapter<String>){
        fromSpinner.setAdapter(coinAdapter)
        toSpinner.setAdapter(coinAdapter)
    }

    private fun moneyToCoin(coinAdapter: ArrayAdapter<String>, moneyAdapter: ArrayAdapter<String>){
        fromSpinner.setAdapter(moneyAdapter)
        toSpinner.setAdapter(coinAdapter)
    }

    private fun coinToMoney(coinAdapter: ArrayAdapter<String>, moneyAdapter: ArrayAdapter<String>){
        fromSpinner.setAdapter(coinAdapter)
        toSpinner.setAdapter(moneyAdapter)
    }

    private fun calculateValue(){

        val coinName = toSpinner.getItems<String>()[toSpinner.selectedIndex]
        val fromCoin = fromSpinner.getItems<String>()[fromSpinner.selectedIndex]

        coinService!!.calculateValue(fromCoin, coinName).enqueue(object : retrofit2.Callback<Coin> {
            override fun onFailure(call: Call<Coin>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Coin>?, response: Response<Coin>?) {
                //SUCCESS
                if(coinName == "BTC"){
                    showData(response!!.body()!!.BTC)
                }else if(coinName == "ETC"){
                    showData(response!!.body()!!.ETC)
                }else if(coinName == "ETH"){
                    showData(response!!.body()!!.ETH)
                }else if(coinName == "XRP"){
                    showData(response!!.body()!!.XRP)
                }else if(coinName == "LTC"){
                    showData(response!!.body()!!.LTC)
                }else if(coinName == "XMR"){
                    showData(response!!.body()!!.XMR)
                }else if(coinName == "DASH"){
                    showData(response!!.body()!!.DASH)
                }else if(coinName == "MAID"){
                    showData(response!!.body()!!.MAID)
                }else if(coinName == "AUR"){
                    showData(response!!.body()!!.AUR)
                }else if(coinName == "XEM"){
                    showData(response!!.body()!!.XEM)
                }else if(coinName == "USD"){
                    showData(response!!.body()!!.USD)
                }else if(coinName == "GBP"){
                    showData(response!!.body()!!.GBP)
                }else if(coinName == "EUR"){
                    showData(response!!.body()!!.EUR)
                }

            }
        })
    }

    private fun showData(coinName: String) {
        if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "BTC"){
            Picasso.get().load(Common.BTC_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "ETC"){
            Picasso.get().load(Common.ETC_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "ETH"){
            Picasso.get().load(Common.ETH_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "XRP"){
            Picasso.get().load(Common.XRP_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "LTC"){
            Picasso.get().load(Common.LTC_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "XMR"){
            Picasso.get().load(Common.XMR_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "DASH"){
            Picasso.get().load(Common.DASH_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "MAID"){
            Picasso.get().load(Common.MAID_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "AUR"){
            Picasso.get().load(Common.AUR_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "XEM"){
            Picasso.get().load(Common.XEM_IMAGE).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "USD"){
            Picasso.get().load(R.mipmap.ic_dollar_sign).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "EUR"){
            Picasso.get().load(R.mipmap.ic_euro_sign).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }else if(toSpinner.getItems<String>().get(toSpinner.selectedIndex).toString() == "GBP"){
            Picasso.get().load(R.mipmap.ic_pound_sign).into(coinImage)
            convertedValueTxtView.setText(coinName)
        }
    }
}
